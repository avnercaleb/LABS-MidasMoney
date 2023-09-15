package com.midas.midasmoneyapi.eventos.listeners;

import com.midas.midasmoneyapi.eventos.EventoRecursoCriado;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ListenerRecursoCriado implements ApplicationListener<EventoRecursoCriado> {

    @Override
    public void onApplicationEvent(EventoRecursoCriado event) {
        HttpServletResponse response = event.getResponse();
        Long id = event.getId();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());

    }
}
