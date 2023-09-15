package com.midas.midasmoneyapi.eventos;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.connector.Response;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;
@Getter
@Setter
public class EventoRecursoCriado extends ApplicationEvent {

    private HttpServletResponse response;
    private Long id;
    public EventoRecursoCriado(Object source, HttpServletResponse response, Long id) {
        super(source);
        this.response = response;
        this.id = id;
    }
}
