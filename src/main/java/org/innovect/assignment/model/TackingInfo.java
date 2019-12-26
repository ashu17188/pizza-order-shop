package org.innovect.assignment.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class TackingInfo {

	/*
	 * @CreatedBy
	 * 
	 * @Column(name = "created_by") private String createdBy;
	 */
	@Column(name = "created_date", updatable = false)
	private Instant createdDate = Instant.now();

	/*
	 * @Column(name = "last_modified_by") private String lastModifiedBy;
	 */
	@Column(name = "last_modified_date")
	private Instant lastModifiedDate = Instant.now();

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Instant lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@PrePersist
	protected void onCreate() {
		createdDate = lastModifiedDate = Instant.now();
	}

	@PreUpdate
	protected void onUpdate() {
		lastModifiedDate = Instant.now();
	}
}
