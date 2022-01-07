package order;

// -*- codeng: utf-8 -*-

import com.alibaba.fastjson.JSONObject;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class bolt1 extends BaseBasicBolt {
        public void execute(Tuple input, BasicOutputCollector collector) {
            try {
                // TODO Auto-generated method stub
                byte[] bytes = input.getBinary(0);  //字节码
                //byte->String
                String line = new String(bytes);
                String[] str = line.split(",");

                String book_name = str[2];
                String book_type = str[3];
                String book_num = str[4];
                String book_press = str[6];

                String[] bne = book_name.split(":");
                String bookname = bne[1];
                System.out.println(bookname);
                String[] btp = book_type.split(":");
                String booktype = btp[1];
                System.out.println(booktype);
                String[] bps = book_press.split(":");
                String bookpress = bps[1];
                System.out.println(bookpress);

                String booknum = "{" + book_num + "}";

                JSONObject jsonobject = JSONObject.parseObject(booknum);
                String Booknum = jsonobject.getString("book_num");
                System.out.println(Booknum);

                //发送数据，发给需要的bolt，后面的bolt按字段索引数据
                collector.emit(new Values(bookname, booktype, Booknum, bookpress));
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        /**
         * 描述将要发送数据的字段是哪些
         */
        declarer.declare(new Fields("bookname","booktype","booknum","bookpress"));
    }

}