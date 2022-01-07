package storm;


import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class Bolt2 extends BaseBasicBolt{
    public void execute(Tuple input, BasicOutputCollector collector) {
        //承接上一个bolt发送的数据，我只需要字段的数据
        String data = input.getString(1);
        System.out.println(data);
    }
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}
