package storm;


import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class Bolt1 extends BaseBasicBolt{

    public void execute(Tuple input, BasicOutputCollector collector) {
        // TODO Auto-generated method stub
        byte[]bytes=input.getBinary(0);  //字节码
        //byte->String
        String line =new String(bytes);
        String[] str=line.split(",");
        String order_id =str[0];
        String book_id=str[1];
        String book_name =str[2];
        String book_type =str[3];
        String book_num=str[4];
        String book_price=str[5];
        String  book_press=str[6];
        String id=str[7];
        

        //发送数据，发给需要的bolt，后面的bolt按字段索引数据
        collector.emit(new Values(order_id,book_id,book_name,book_type,book_num,book_price,book_press,id));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        /**
         * 描述我将要发送数据的字段是哪些
         */
        declarer.declare(new Fields("order_id", "book_id", "book_name","book_type","book_num","book_price",
                "book_press","id"));
    }

}
