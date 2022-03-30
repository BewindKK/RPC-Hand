package com.bewind.client;

import com.bewind.rpc.Peer;
import com.bewind.rpc.utils.ReflectionUtils;
import com.bewind.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomTransportSelector implements TransportSelector{

    /**
     * 已经连接好的client池
     */
    private List<TransportClient> clients;

    public RandomTransportSelector() {
        clients=new ArrayList<>();
    }

    @Override
    public synchronized void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count=Math.max(count,1);//大于等于1
        for(Peer peer:peers){
            for (int i=0;i<count;i++){
                TransportClient client= ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connent server：{}",peer);
        }
    }

    public synchronized TransportClient select() {
        int i= new Random().nextInt(clients.size());
        //获取一个连接
        return clients.get(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    //ArrayList方法并不是线程安全的，因此要加上synchronized
    @Override
    public synchronized void close() {
        //把所有的client关掉
        for (TransportClient client:clients){
            client.close();
        }
        clients.clear();
    }
}
