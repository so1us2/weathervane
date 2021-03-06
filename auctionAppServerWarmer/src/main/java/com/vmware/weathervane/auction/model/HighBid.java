/*
Copyright 2017-2019 VMware, Inc.
SPDX-License-Identifier: BSD-2-Clause
*/
package com.vmware.weathervane.auction.model;

import java.io.Serializable;
import java.util.Date;

public class HighBid implements Serializable, DomainObject {

	private static final long serialVersionUID = 1L;

	public enum HighBidState { OPEN, LASTCALL, SOLD };

	private Long id;
	private Float amount;
	private HighBidState state;

	private Integer bidCount;
	private Date biddingStartTime;
	private Date biddingEndTime;
	private Date currentBidTime;

	// References to other entities
	private Auction auction;
	private Item item;
	private User bidder;
	
	// reference to documents in MongoDB
	private String bidId;

	/*
	 * These fields are included to enable a 
	 * HighBid to carry this information
	 * without fetching the related entities. 
	 */
	private Long auctionId;
	private Long itemId;
	private Long bidderId;
	
	/*
	 * These flags exists to provide information that simplifies
	 * preloading and prepare benchmark runs.  It is not used by 
	 * the Auction application
	 */
	private boolean preloaded;
	
	private Integer version;

	public HighBid() {

	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public HighBidState getState() {
		return state;
	}

	public void setState(HighBidState bidState) {
		this.state = bidState;
	}

	public Integer getBidCount() {
		return bidCount;
	}

	public void setBidCount(Integer bidCount) {
		this.bidCount = bidCount;
	}

	public Date getBiddingStartTime() {
		return biddingStartTime;
	}

	public void setBiddingStartTime(Date biddingStartTime) {
		this.biddingStartTime = biddingStartTime;
	}

	public Date getBiddingEndTime() {
		return biddingEndTime;
	}

	public void setBiddingEndTime(Date biddingEndTime) {
		this.biddingEndTime = biddingEndTime;
	}

	public Date getCurrentBidTime() {
		return currentBidTime;
	}

	public void setCurrentBidTime(Date currentBidTime) {
		this.currentBidTime = currentBidTime;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		// defer to the item to connect the two
		this.item = item;
		if (item != null) {
			this.itemId = item.getId();
		} else {
			this.itemId = -1L;
		}
	}

	public User getBidder() {
		return bidder;
	}

	public void setBidder(User bidder) {
		this.bidder = bidder;
		if (bidder != null) {
			this.bidderId = bidder.getId();
		} else {
			this.bidderId = -1L;
		}
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
		if (auction != null) {
			this.auctionId = auction.getId();
		} else {
			this.auctionId = -1L;
		}
	}

	public String getBidId() {
		return bidId;
	}

	public void setBidId(String bidid) {
		this.bidId = bidid;
	}

	public boolean isPreloaded() {
		return preloaded;
	}

	public void setPreloaded(boolean preloaded) {
		this.preloaded = preloaded;
	}

	public Long getAuctionId() {
		return auctionId;
	}

	private void setAuctionId(Long auctionId) {
		this.auctionId = auctionId;
	}

	public Long getItemId() {
		return itemId;
	}

	private void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getBidderId() {
		return bidderId;
	}

	private void setBidderId(Long bidderId) {
		this.bidderId = bidderId;
	}

	@Override
	public String toString() {
		String bidString;
		
		bidString = "HighBid Id: " + id 
				+ " auctionId = " + auctionId
				+ " itemId = " + itemId
				+ " bidCount = " + bidCount
				+ " amount : " + amount 
				+ " state : " + state;
		
		return bidString;		
	}

}
