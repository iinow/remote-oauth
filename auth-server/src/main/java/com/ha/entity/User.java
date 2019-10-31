package com.ha.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ha.common.AuthDefine.PROVIDER;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {
	private static final long serialVersionUID = -4629059576114649501L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "email", nullable = false)
	@Email
	private String email;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "image_url", nullable = true)
	private String imageUrl;
	
	@Column(name = "password", nullable = true)
	private String password;
	
	@NotNull
    @Enumerated(EnumType.ORDINAL)
	private PROVIDER provider;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date cdt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date udt;
}
