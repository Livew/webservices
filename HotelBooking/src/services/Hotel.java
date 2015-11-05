package services;

import java.util.Collection;

import javax.xml.bind.annotation.XmlTransient;

public class Hotel {
	
	private Integer hotelId;
	private String hotelName;
	private int roomsAvailable;
	private Collection<Booking> bookingCollection;
	
	public Hotel() {
    }

    public Hotel(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Hotel(Integer hotelId, String hotelName, int roomsAvailable) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.roomsAvailable = roomsAvailable;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getRoomsAvailable() {
        return roomsAvailable;
    }

    public void setRoomsAvailable(int roomsAvailable) {
        this.roomsAvailable = roomsAvailable;
    }

    @XmlTransient
    public Collection<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hotelId != null ? hotelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.hotelId == null && other.hotelId != null) || (this.hotelId != null && !this.hotelId.equals(other.hotelId))) {
            return false;
        }
        return true;
    }
}
