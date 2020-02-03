package org.innovect.assignment.hateoas.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent extends ApplicationEvent {
    private final HttpServletResponse response;
    private long idOfNewResource1;
    private String idOfNewResource2;

    
    public ResourceCreatedEvent(final Object source, final HttpServletResponse response, final long idOfNewResource) {
		super(source);
        this.response = response;
        this.idOfNewResource1 = idOfNewResource;
    }

    public ResourceCreatedEvent(final Object source, final HttpServletResponse response, String idOfNewResource2) {
		super(source);
        this.response = response;
        this.idOfNewResource2 = idOfNewResource2;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public long getIdOfNewResource() {
        return idOfNewResource1;
    }

	public String getIdOfNewResource2() {
		return idOfNewResource2;
	}

}
