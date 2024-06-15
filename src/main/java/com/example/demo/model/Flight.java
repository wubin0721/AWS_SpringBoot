package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
public class Flight {

  //航班Id
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long id;

  private String airNo;
  private String airName;
  private Integer price;

  private String departure;
  private String arrival;
  private String departureDate;
  private String arrivalDate;
  private String startTime;
  private String endTime;

  private Boolean isDirectFlight;

  private Integer standby;
}
