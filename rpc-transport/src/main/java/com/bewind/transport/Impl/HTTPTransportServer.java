package com.bewind.transport.Impl;

import com.bewind.transport.RequestHandler;
import com.bewind.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class HTTPTransportServer implements TransportServer {
    private RequestHandler handler;
    private Server server;

    //TODO 理解代码
    @Override
    public void init(int port, RequestHandler handler) {
        this.handler=handler;
        this.server=new Server(port);

        //servlet接受请求
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        server.setHandler(servletContextHandler);

        //holder是jetty在处理网络请求的一个抽象
        ServletHolder servletHolder = new ServletHolder(new RequestServlet());
        servletContextHandler.addServlet(servletHolder,"/*");
    }

    @Override
    public void start() {
        try {
            server.start();
            //不让它立马返回，让server等
            server.join();
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    class RequestServlet extends HttpServlet{
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");
            InputStream inputStream = req.getInputStream();
            OutputStream outputStream=resp.getOutputStream();

            if (handler!=null){
                handler.onRequest(inputStream,outputStream);
            }
            outputStream.flush();
        }
    }
}
