package us.ihmc.avatar.networkProcessor.modules;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.BindException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToolboxModuleTest
{
   @Test
   public void testDetectsDirectBindException()
   {
      assertTrue(ToolboxModule.isYoVariableServerPortBindFailure(new BindException("Address already in use")));
   }

   @Test
   public void testDetectsNestedBindException()
   {
      assertTrue(ToolboxModule.isYoVariableServerPortBindFailure(new RuntimeException(new BindException("Address already in use"))));
   }

   @Test
   public void testDetectsNativeBindMessage()
   {
      assertTrue(ToolboxModule.isYoVariableServerPortBindFailure(new RuntimeException(new IOException("bind(..) failed: Address already in use"))));
   }

   @Test
   public void testIgnoresUnrelatedIOException()
   {
      assertFalse(ToolboxModule.isYoVariableServerPortBindFailure(new RuntimeException(new IOException("connection reset"))));
   }

   @Test
   public void testIgnoresNull()
   {
      assertFalse(ToolboxModule.isYoVariableServerPortBindFailure(null));
   }
}
