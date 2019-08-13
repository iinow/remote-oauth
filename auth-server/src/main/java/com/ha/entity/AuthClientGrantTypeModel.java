package com.ha.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Setter
@Table(name = "client_granttypes")
public class AuthClientGrantTypeModel extends BaseModel {
	private static final long serialVersionUID = -1923402086737063127L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "clientId")
	private AuthClientModel client;
	
	@Column(name = "grant_type")
	private String grantType;
}
