package org.innovect.assignment.config;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.innovect.assignment.hateoas.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.common.base.Preconditions;

@Component
class ResourceCreatedDiscoverabilityListener implements ApplicationListener<ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(final ResourceCreatedEvent resourceCreatedEvent) {
        Preconditions.checkNotNull(resourceCreatedEvent);

        final HttpServletResponse response = resourceCreatedEvent.getResponse();
        final long longId = resourceCreatedEvent.getIdOfNewResource();
        final String stringId = resourceCreatedEvent.getIdOfNewResource2();

        addLinkHeaderOnResourceCreation(response, longId,stringId);
    }

    void addLinkHeaderOnResourceCreation(final HttpServletResponse response, final long idOfNewResource,final String idString) {
        // final String requestUrl = request.getRequestURL().toString();
        // final URI uri = new UriTemplate("{requestUrl}/{idOfNewResource}").expand(requestUrl, idOfNewResource);
    	 URI uri = null;
         if(!StringUtils.isEmpty(idString)) {
        	 uri= ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{idString}").buildAndExpand(idString).toUri();
         }else {
        	 uri= ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{idOfNewResource}").buildAndExpand(idOfNewResource).toUri();        	 
         }

        response.setHeader(HttpHeaders.LOCATION, uri.toASCIIString());
    }

}