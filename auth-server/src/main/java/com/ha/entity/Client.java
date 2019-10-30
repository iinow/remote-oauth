package com.ha.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ha.common.AuthDefine.OAuthResource;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
@Entity
@Table(name = "clients")
public class Client extends BaseEntity {
	private static final long serialVersionUID = -5045155070119647772L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "clientId")
	private String clientId;
	
	@Column(name = "secret")
	private String secret;
	
	@Column(name = "redirectUri")
	private String redirectUri;
	
	@Column(name = "type")
	private Integer type;
	
	@OneToMany(mappedBy = "client")
	private List<ClientScope> scopes = new ArrayList<>();
	
	@OneToMany(mappedBy = "client")
	private List<ClientGrantType> granttypes = new ArrayList<>();
	
	public OAuthResource findResource() {
		return OAuthResource.findOAuthResource(getType());
	}
}
