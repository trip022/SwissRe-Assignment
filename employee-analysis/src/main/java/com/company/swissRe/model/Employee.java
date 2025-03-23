package com.company.swissRe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	private int id;
	private String firstName;
	private String lastName;
	private double salary;
	private Employee manager; // Manager field to store the reference to the manager
	private Integer managerId; // Manager ID to be used for linking in the service
}
