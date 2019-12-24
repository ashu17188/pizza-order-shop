package org.innovect.assignment.model;

import java.time.Instant;

import javax.persistence.Column;

import org.springframework.data.annotation.CreatedBy;

public abstract class TackingInfo {

	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Instant createdDate = Instant.now();

	@Column(name = "last_modified_by")
	private String lastModifiedBy;

	@Column(name = "last_modified_date")
	private Instant lastModifiedDate = Instant.now();

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
