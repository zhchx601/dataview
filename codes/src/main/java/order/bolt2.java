package order;

// -*- codeing: utf-8 -*-

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

public class bolt2 extends BaseBasicBolt{
    public void execute(Tuple input, BasicOutputCollector collector) {
        try{
            Jedis jedis = new Jedis("127.0.0.1",6379);//连接jedis数据
            //承接上一个bolt发送的数据，只需要字段的数据
            String bookname = input.getStringByField("bookname");
            if(bookname!=null) {
                    String booknum = input.getStringByField("booknum");
                    Float Booknum = Float.parseFloat(booknum);
                    jedis.zincrby("top10_book",Booknum,bookname);

                String bookpress = input.getStringByField("bookpress");
                if (bookpress != null)
                    jedis.zincrby("top10_press", 1, bookpress);
                String booktype = input.getStringByField("booktype");
                if (booktype != null)
                    jedis.zincrby("top10_type", 1, booktype);
            }
         }
        catch (Exception e){
            e.printStackTrace();
        }


    }
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}
