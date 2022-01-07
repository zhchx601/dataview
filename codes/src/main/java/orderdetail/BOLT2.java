package orderdetail;

// -*- codeing: utf-8 -*-

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

public class BOLT2 extends BaseBasicBolt{
        public void execute(Tuple input, BasicOutputCollector collector) {
            try {
                Jedis jedis = new Jedis("127.0.0.1", 6379);//连接jedis数
                //承接上一个bolt发送的数据，我只需要字段的数据
                String total = input.getString(0);
//       System.out.println(data1);
//       String total_num = input.getString(0);
                Float Total = Float.parseFloat(total);
                jedis.incrByFloat("total_money", Total);
                String Orderid = input.getString(1);
                if (Orderid != null)
                    jedis.incrBy("order_num", 1);
                String sender = input.getString(2);
                if (sender != null)
                    jedis.incrBy("customer_num", 1);
                String province = input.getString(3);
                System.out.println(province);
                if (province != null)
                    jedis.zincrby("area_num", 1, province);
                String payment = input.getString(4);
                System.out.println(payment);
                if (payment != null)
                    jedis.zincrby("payment", 1, payment);
            }
         catch (Exception e){
             e.printStackTrace();
                }

    }
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}
