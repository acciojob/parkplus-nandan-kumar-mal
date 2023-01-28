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

            User user = userRepository3.findById(userId).get();

            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

            List<Spot> spotList = parkingLot.getSpotList();


            List<Reservation> reservationList = new ArrayList<>();

            Reservation reservation = new Reservation();
            Boolean spotAvailable = false;
            for (Spot spot : spotList) {
                if (!spot.getOccupied()) {

                    SpotType spotType = spot.getSpotType();
                    if (numberOfWheels >= 2 && (spotType == SpotType.TWO_WHEELER || spotType == SpotType.FOUR_WHEELER)) {
                        spotAvailable=true;
                        spot.setOccupied(true);
                    } else if (numberOfWheels >= 4 && spotType == SpotType.OTHERS) {
                        spotAvailable=true;
                        spot.setOccupied(true);
                    } else {
                        continue;
                    }
                }
                reservation.setSpot(spot);
                reservation.setNumberOfHours(timeInHours);
                reservation.setUser(user);
                reservation.setBillAmount(spot.getPricePerHour() * timeInHours);

                reservationList.add(reservation);
                spot.setReservationList(reservationList);
                user.setReservationList(reservationList);

                parkingLotRepository3.save(parkingLot);
                spotRepository3.save(spot);
                userRepository3.save(user);
                reservationRepository3.save(reservation);

                break;

            }
            if(!spotAvailable)
                throw new Exception("the reservation cannot be made");

            return reservation;




    }
}
