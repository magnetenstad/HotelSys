package gr2116.ui.main;

import gr2116.core.HotelRoom;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Sorts a collection of Hotelrooms.
 */
public class HotelRoomSorter {
  private SortProperty sortProperty = SortProperty.ByRoomNumber;

  private enum SortProperty {
    ByPrice((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getPrice() < roomB.getPrice() ? -1 : 1;
    }),
    ByPriceDecreasing((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getPrice() > roomB.getPrice() ? -1 : 1;
    }),
    ByRoomNumber((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getNumber() < roomB.getNumber() ? -1 : 1;
    }),
    ByRoomNumberDecreasing((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getNumber() > roomB.getNumber() ? -1 : 1;
    }),
    ByAmenityCount((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getAmenities().size() < roomB.getAmenities().size() ? -1 : 1;
    }),
    ByAmenityCountDecreasing((HotelRoom roomA, HotelRoom roomB) -> {
      return roomA.getAmenities().size() > roomB.getAmenities().size() ? -1 : 1;
    });

    private Comparator<HotelRoom> comparator;

    private SortProperty(Comparator<HotelRoom> comparator) {
      this.comparator = comparator;
    }

    public Comparator<HotelRoom> getComparator() {
      return comparator;
    }
  }

  public List<HotelRoom> sortRooms(Collection<HotelRoom> hotelRooms) {
    return hotelRooms.stream().sorted(sortProperty.getComparator()).toList();
  }

  /**
   * Sets the sort property to sort by price.
   * Switches between increasing and decreasing if property is already price.
   */
  public void sortByPrice() {
    if (sortProperty == SortProperty.ByPrice) {
      sortProperty = SortProperty.ByPriceDecreasing;
    } else {
      sortProperty = SortProperty.ByPrice;
    }
  }

  /**
   * Sets the sort property to sort by room number.
   * Switches between increasing and decreasing if property is already room number.
   */
  public void sortByRoomNumber() {
    if (sortProperty == SortProperty.ByRoomNumber) {
      sortProperty = SortProperty.ByRoomNumberDecreasing;
    } else {
      sortProperty = SortProperty.ByRoomNumber;
    }
  }
  
  /**
   * Sets the sort property to sort by anemity count.
   * Switches between increasing and decreasing if property is already amenity count.
   */
  public void sortByAmenityCount() {
    if (sortProperty == SortProperty.ByAmenityCountDecreasing) {
      sortProperty = SortProperty.ByAmenityCount;
    } else {
      sortProperty = SortProperty.ByAmenityCountDecreasing;
    }
  }
}
