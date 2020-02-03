package org.innovect.assignment.utils;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class RestPreconditions extends ResponseStatusException {

	private static final long serialVersionUID = 5741027167458857942L;

	public static <T> T check(final T resource, HttpStatus status, String errorMsg) {
		if (resource == null) {
			throw new ResponseStatusException(Optional.ofNullable(status).orElse(HttpStatus.NOT_FOUND),
					Optional.ofNullable(errorMsg).orElse("Resouce not found"));
		}

		return resource;
	}

	public RestPreconditions(HttpStatus status) {
		super(status);
	}

	public RestPreconditions(HttpStatus status, String reason) {
		super(status, reason);
	}

}
