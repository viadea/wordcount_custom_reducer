import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.NumberFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapred.FileAlreadyExistsException;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.TaskID;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.ReflectionUtils;

public class myOutputFormat<K, V>
  extends TextOutputFormat<K, V>
{
  private FileOutputCommitter myCommitter = null;
  protected static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();
  
  static
  {
    NUMBER_FORMAT.setMinimumIntegerDigits(5);
    NUMBER_FORMAT.setGroupingUsed(false);
  }
  
  public synchronized OutputCommitter getOutputCommitter(TaskAttemptContext context)
    throws IOException
  {
    if (this.myCommitter == null)
    {
      Path output = new Path(getOutputDir(context));
      this.myCommitter = new FileOutputCommitter(output, context);
    }
    return this.myCommitter;
  }
  
  protected static String getOutputDir(TaskAttemptContext context)
  {
    int taskID = context.getTaskAttemptID().getTaskID().getId();
    String outputBaseDir = getOutputPath(context) + "/part_" + NUMBER_FORMAT.format(taskID);
    // String outputBaseDir = getOutputPath(context) + "/part_static" ; 
    return outputBaseDir;
  }
  
}
