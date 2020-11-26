package org.canteen.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "check")
public class Check {

   @Id
   @Column(name = "id_check")
}
