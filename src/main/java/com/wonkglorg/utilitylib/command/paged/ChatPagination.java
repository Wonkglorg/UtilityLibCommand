package com.wonkglorg.utilitylib.command.paged;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ChatPagination<T>{
	private Audience audience;
	private List<T> entries;
	@Getter
	@Setter
	private int pageSize = 25;
	@Getter
	private int page = 0;
	@Setter
	@Getter
	private boolean sendHeaderAndFooterOnEmpty = true;
	
	public ChatPagination(List<T> entries) {
		this.entries = entries;
	}
	
	public void sendToAudience(Audience audience) {
		this.audience = audience;
		
		if(sendHeaderAndFooterOnEmpty || !entries.isEmpty()){
			List<Component> header = constructHeader();
			if(!header.isEmpty()){
				header.forEach(audience::sendMessage);
			}
		}
		
		if(sendHeaderAndFooterOnEmpty || !entries.isEmpty()){
			int from = page * pageSize;
			int to = Math.min(from + pageSize, entries.size());
			List<T> pageResults = entries.subList(from, to);
			int entryCount = from;
			for(var entry : pageResults){
				List<Component> message = constructEntry(entryCount++, entry);
				if(message.isEmpty()){
					continue;
				}
				message.forEach(audience::sendMessage);
			}
		} else {
			List<Component> empty = constructNoResults();
			if(!empty.isEmpty()){
				empty.forEach(audience::sendMessage);
			}
			if(!sendHeaderAndFooterOnEmpty){
				return;
			}
		}
		
		List<Component> footer = constructFooter();
		if(!footer.isEmpty()){
			footer.forEach(audience::sendMessage);
		}
		
		List<Component> pageControls = pageControls();
		if(!pageControls.isEmpty()){
			pageControls.forEach(audience::sendMessage);
		}
	}
	
	protected abstract @NotNull List<Component> constructNoResults();
	
	protected abstract @NotNull List<Component> constructHeader();
	
	protected abstract @NotNull List<Component> constructEntry(int entryCount, T entry);
	
	protected abstract @NotNull List<Component> constructFooter();
	
	protected abstract @NotNull List<Component> pageControls();
	
	public void setPage(int page) {
		this.page = Math.max(0, Math.min(page, getMaxPage()));
	}
	
	public void nextPage() {
		page = Math.min(page + 1, getMaxPage());
	}
	
	public void prevPage() {
		page = Math.max(page - 1, 0);
	}
	
	public int getMaxPage() {
		return entries.size() / pageSize;
	}
	
	/**
	 * @return returns the 1 indexed page for user display
	 */
	public int getPageDisplay() {
		return page + 1;
	}
	
	/**
	 * @return returns the 1 indexed maxpage for user display
	 */
	public int getMaxPageDisplay() {
		return getMaxPage() + 1;
	}
	
}
