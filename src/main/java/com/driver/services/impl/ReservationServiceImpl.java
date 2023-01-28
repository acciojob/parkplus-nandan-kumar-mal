package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

       try {
           if (!userRepository3.findById(userId).isPresent() || !parkingLotRepository3.findById(parkingLotId).isPresent())
               throw new Exception("Cannot make reservation");

           User user = userRepository3.findById(userId).get();

           ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

           List<Spot> spotList = parkingLot.getSpotList();


           List<Reservation> reservationList = new ArrayList<>();

           Reservation reservation = new Reservation();
           int minPrice = Integer.MAX_VALUE;
           Boolean spotAvailable = false;
           Spot parkingSpot=null;
           for (Spot spot : spotList) {
               int currentSpotPrice = spot.getPricePerHour() * timeInHours;
               if (!spot.getOccupied()) {

                   SpotType spotType = spot.getSpotType();
                   if (numberOfWheels >= 2 && (spotType == SpotType.TWO_WHEELER || spotType == SpotType.FOUR_WHEELER)) {
                       if (currentSpotPrice < minPrice) {
                           minPrice = currentSpotPrice;
                           spotAvailable = true;
                           parkingSpot = spot;
                           parkingSpot.setOccupied(true);
                       }
                   } else if (numberOfWheels >= 4 && (spotType == SpotType.FOUR_WHEELER || spotType == SpotType.OTHERS)) {
                       if (currentSpotPrice < minPrice) {
                           minPrice = currentSpotPrice;
                           spotAvailable = true;
                           parkingSpot = spot;
                           parkingSpot.setOccupied(true);
                       }
                   } else continue;
               }
           }

                   reservation.setSpot(parkingSpot);
                   reservation.setNumberOfHours(timeInHours);
                   reservation.setUser(user);
                   reservation.setBillAmount(parkingSpot.getPricePerHour() * timeInHours);

                   reservationList.add(reservation);
                   parkingSpot.setReservationList(reservationList);
                   user.setReservationList(reservationList);

                   parkingLotRepository3.save(parkingLot);
                   spotRepository3.save(parkingSpot);
                   userRepository3.save(user);
                   reservationRepository3.save(reservation);


           if (!spotAvailable)
               throw new Exception("Cannot make reservation");

           return reservation;
       }catch (Exception e){
           return  null;
       }




    }
}
