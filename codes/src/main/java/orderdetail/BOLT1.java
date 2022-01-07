package orderdetail;

// -*- codeing: utf-8 -*-

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;


public class BOLT1 extends BaseBasicBolt {

    public void execute(Tuple input, BasicOutputCollector collector) {
        try {
            // TODO Auto-generated method stub
            byte[] bytes = input.getBinary(0);  //字节码
            //byte->String
            String line = new String(bytes);
            String[] str = line.split(",");
            String Total = str[9];
            String ttl = Total.substring(0, Total.length() - 2);
            String[] tt = ttl.split(":");
            String total = tt[1].substring(1, tt[1].length() - 1);
            String order_id = str[1];
            String[] od = order_id.split(":");
            String orderid = od[1];
            String Sender = str[1];
            String[] sd = Sender.split(":");
            String sender = sd[1];
            String receiveplace=str[5];
            String [] rpe=receiveplace.split(":");
            String province=rpe[1].substring(1,3);
            String payment=str[6];
            String[] pm=payment.split(":");
            String methodpayment=pm[1];
            //System.out.println(totall);
            //发送数据，发给需要的bolt，后面的bolt按字段索引数据
            collector.emit(new Values(total, orderid, sender,province,methodpayment));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        /**
         * 描述将要发送数据的字段是哪些
         */
        declarer.declare(new Fields("total","orderid","sender","province","methodpayment"));
    }

}
