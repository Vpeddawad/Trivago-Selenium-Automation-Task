package com.base.aplication.trivago.hotel;

public class Hotel {
	/**
	 * 
	 */
	public String hotelName;
	public String hotel_OUR_Loest_Price;
	public boolean Free_cancel;

	public boolean getFree_cancel() {
		return Free_cancel;
	}

	public void setFree_cancel(boolean free_cancel) {
		Free_cancel = free_cancel;
	}

	public String gethotel_OUR_Loest_Price() {
		return hotel_OUR_Loest_Price;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public void sethotel_OUR_Loest_Price(String hotelPrice) {
		this.hotel_OUR_Loest_Price = hotelPrice;
	}

	public String getHotelName() {
		return hotelName;
	}
}
