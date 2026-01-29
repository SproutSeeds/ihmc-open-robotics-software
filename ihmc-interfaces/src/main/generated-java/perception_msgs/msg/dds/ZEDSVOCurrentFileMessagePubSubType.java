package perception_msgs.msg.dds;

/**
* 
* Topic data type of the struct "ZEDSVOCurrentFileMessage" defined in "ZEDSVOCurrentFileMessage_.idl". Use this class to provide the TopicDataType to a Participant. 
*
* This file was automatically generated from ZEDSVOCurrentFileMessage_.idl by us.ihmc.idl.generator.IDLGenerator. 
* Do not update this file directly, edit ZEDSVOCurrentFileMessage_.idl instead.
*
*/
public class ZEDSVOCurrentFileMessagePubSubType implements us.ihmc.pubsub.TopicDataType<perception_msgs.msg.dds.ZEDSVOCurrentFileMessage>
{
   public static final java.lang.String name = "perception_msgs::msg::dds_::ZEDSVOCurrentFileMessage_";
   
   @Override
   public final java.lang.String getDefinitionChecksum()
   {
   		return "4ef38358d7786da656ea6467893e9692252610387aa868da52ff5bea4b6f0285";
   }
   
   @Override
   public final java.lang.String getDefinitionVersion()
   {
   		return "local";
   }

   private final us.ihmc.idl.CDR serializeCDR = new us.ihmc.idl.CDR();
   private final us.ihmc.idl.CDR deserializeCDR = new us.ihmc.idl.CDR();

   @Override
   public void serialize(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data, us.ihmc.pubsub.common.SerializedPayload serializedPayload) throws java.io.IOException
   {
      serializeCDR.serialize(serializedPayload);
      write(data, serializeCDR);
      serializeCDR.finishSerialize();
   }

   @Override
   public void deserialize(us.ihmc.pubsub.common.SerializedPayload serializedPayload, perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data) throws java.io.IOException
   {
      deserializeCDR.deserialize(serializedPayload);
      read(data, deserializeCDR);
      deserializeCDR.finishDeserialize();
   }

   public static int getMaxCdrSerializedSize()
   {
      return getMaxCdrSerializedSize(0);
   }

   public static int getMaxCdrSerializedSize(int current_alignment)
   {
      int initial_alignment = current_alignment;

      current_alignment += 4 + us.ihmc.idl.CDR.alignment(current_alignment, 4) + 255 + 1;
      current_alignment += 8 + us.ihmc.idl.CDR.alignment(current_alignment, 8);

      current_alignment += 8 + us.ihmc.idl.CDR.alignment(current_alignment, 8);


      return current_alignment - initial_alignment;
   }

   public final static int getCdrSerializedSize(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data)
   {
      return getCdrSerializedSize(data, 0);
   }

   public final static int getCdrSerializedSize(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data, int current_alignment)
   {
      int initial_alignment = current_alignment;

      current_alignment += 4 + us.ihmc.idl.CDR.alignment(current_alignment, 4) + data.getCurrentFileName().length() + 1;

      current_alignment += 8 + us.ihmc.idl.CDR.alignment(current_alignment, 8);


      current_alignment += 8 + us.ihmc.idl.CDR.alignment(current_alignment, 8);



      return current_alignment - initial_alignment;
   }

   public static void write(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data, us.ihmc.idl.CDR cdr)
   {
      if(data.getCurrentFileName().length() <= 255)
      cdr.write_type_d(data.getCurrentFileName());else
          throw new RuntimeException("current_file_name field exceeds the maximum length: %d > %d".formatted(data.getCurrentFileName().length(), 255));

      cdr.write_type_11(data.getCurrentPosition());

      cdr.write_type_11(data.getLength());

   }

   public static void read(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data, us.ihmc.idl.CDR cdr)
   {
      cdr.read_type_d(data.getCurrentFileName());	
      data.setCurrentPosition(cdr.read_type_11());
      	
      data.setLength(cdr.read_type_11());
      	

   }

   @Override
   public final void serialize(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data, us.ihmc.idl.InterchangeSerializer ser)
   {
      ser.write_type_d("current_file_name", data.getCurrentFileName());
      ser.write_type_11("current_position", data.getCurrentPosition());
      ser.write_type_11("length", data.getLength());
   }

   @Override
   public final void deserialize(us.ihmc.idl.InterchangeSerializer ser, perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data)
   {
      ser.read_type_d("current_file_name", data.getCurrentFileName());
      data.setCurrentPosition(ser.read_type_11("current_position"));
      data.setLength(ser.read_type_11("length"));
   }

   public static void staticCopy(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage src, perception_msgs.msg.dds.ZEDSVOCurrentFileMessage dest)
   {
      dest.set(src);
   }

   @Override
   public perception_msgs.msg.dds.ZEDSVOCurrentFileMessage createData()
   {
      return new perception_msgs.msg.dds.ZEDSVOCurrentFileMessage();
   }
   @Override
   public int getTypeSize()
   {
      return us.ihmc.idl.CDR.getTypeSize(getMaxCdrSerializedSize());
   }

   @Override
   public java.lang.String getName()
   {
      return name;
   }
   
   public void serialize(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data, us.ihmc.idl.CDR cdr)
   {
      write(data, cdr);
   }

   public void deserialize(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage data, us.ihmc.idl.CDR cdr)
   {
      read(data, cdr);
   }
   
   public void copy(perception_msgs.msg.dds.ZEDSVOCurrentFileMessage src, perception_msgs.msg.dds.ZEDSVOCurrentFileMessage dest)
   {
      staticCopy(src, dest);
   }

   @Override
   public ZEDSVOCurrentFileMessagePubSubType newInstance()
   {
      return new ZEDSVOCurrentFileMessagePubSubType();
   }
}
