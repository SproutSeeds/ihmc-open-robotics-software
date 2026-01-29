package us.ihmc.rdx.perception;

import imgui.ImGui;
import imgui.type.ImInt;
import perception_msgs.msg.dds.ZEDSVOCurrentFileMessage;
import std_msgs.msg.dds.Int64;
import us.ihmc.commons.thread.Throttler;
import us.ihmc.communication.PerceptionAPI;
import us.ihmc.communication.ros2.ROS2Helper;
import us.ihmc.rdx.imgui.ImGuiTools;
import us.ihmc.rdx.imgui.ImGuiUniqueLabelMap;
import us.ihmc.rdx.imgui.RDXPanel;
import us.ihmc.rdx.ui.RDXBaseUI;

public class RDXZEDSVOPlayerPanel extends RDXPanel
{
   private static final String PANEL_NAME = "ZED SVO Player";

   private final ImGuiUniqueLabelMap labels = new ImGuiUniqueLabelMap(getClass());
   private final Throttler requestThrottler = new Throttler().setFrequency(5.0);

   private final ROS2Helper ros2Helper;
   private final RDXBaseUI baseUI;

   private ZEDSVOCurrentFileMessage latestMessage;
   private final ImInt requestedPosition = new ImInt();
   private boolean holdingOnToTheSlider;
   private boolean paused;

   public RDXZEDSVOPlayerPanel(ROS2Helper ros2Helper, RDXBaseUI baseUI)
   {
      super(PANEL_NAME);

      this.ros2Helper = ros2Helper;
      this.baseUI = baseUI;

      ros2Helper.subscribeViaCallback(PerceptionAPI.ZED_SVO_CURRENT_FILE, message -> this.latestMessage = message);
   }

   public void update()
   {
      boolean overlayPanelExists = baseUI.getPrimary3DPanel().overlayPanelExists(PANEL_NAME);

      if (!overlayPanelExists && latestMessage != null)
      {
         baseUI.getPrimary3DPanel().addOverlayPanel(PANEL_NAME, this::render);
      }
   }

   public void render()
   {
      // Because of threading, it's possible that we haven't received any message so we can't render anything yet
      if (latestMessage == null)
         return;

      ImGuiTools.textBold("Current SVO:");
      ImGui.sameLine();
      ImGui.textWrapped(latestMessage.getCurrentFileName().toString());

      if (!holdingOnToTheSlider)
         requestedPosition.set((int) latestMessage.getCurrentPosition());

      if (ImGuiTools.sliderInt(labels.get("Position"), requestedPosition, 0, Math.max((int) latestMessage.getLength(), 0)))
      {
         holdingOnToTheSlider = true;

         if (requestThrottler.run())
         {
            publishPositionRequest();
         }
      }
      // Called once you let go of the slider
      if (ImGui.isItemDeactivatedAfterEdit())
      {
         holdingOnToTheSlider = false;

         publishPositionRequest();
      }

      ImGui.sameLine();

      if (ImGui.button(labels.get(paused ? "Play" : "Pause")))
      {
         ros2Helper.publish(paused ? PerceptionAPI.ZED_SVO_PLAY : PerceptionAPI.ZED_SVO_PAUSE);
         paused = !paused;
      }

      ImGui.beginDisabled(!paused);
      if (ImGui.button(labels.get("Previous Frame")))
      {
         requestedPosition.set((int) latestMessage.getCurrentPosition() - 1);
         publishPositionRequest();
      }
      ImGui.sameLine();
      if (ImGui.button(labels.get("Next Frame")))
      {
         requestedPosition.set((int) latestMessage.getCurrentPosition() + 1);
         publishPositionRequest();
      }
      ImGui.endDisabled();
   }

   private void publishPositionRequest()
   {
      Int64 positionMessage = new Int64();
      positionMessage.setData(requestedPosition.get());
      ros2Helper.publish(PerceptionAPI.ZED_SVO_SET_POSITION, positionMessage);
   }
}
