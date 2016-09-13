package BrazilCenter.Monitor.server;

import java.util.LinkedList;
import java.util.List;

import BrazilCenter.DaoUtils.Utils.LogUtils;
import BrazilCenter.Monitor.Utils.Utils;
import BrazilCenter.Tcp.Server.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class MonitorServerHandler extends ServerHandler {
	private String cacheBuf = "";

	private String GetSingleMsg(String msg, List<String> list) {
		String singleMsg = null;
		int begin = msg.indexOf("<?");
		int end = msg.indexOf("</info>", begin);
		if (begin >= 0 && end > begin) {
			singleMsg = msg.substring(begin, end + "</info>".length());
			list.add(singleMsg);

			singleMsg = msg.substring(end + "</info>".length());
			if (singleMsg.length() > (end - begin)) {
				return this.GetSingleMsg(singleMsg, list);
			}
		}else{
			singleMsg = msg;
		}
		return singleMsg;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object Inmsg) {
		// TODO Auto-generated method stub
		super.channelRead(ctx, Inmsg);

		ByteBuf buf = (ByteBuf) Inmsg;
		String msg = this.getMessage(buf);
		String tmpMsg = cacheBuf + msg;
		List<String> tmplist = new LinkedList<String>();
		cacheBuf = this.GetSingleMsg(tmpMsg, tmplist);

		for (int i = 0; i < tmplist.size(); i++) {

			Utils.MessageList.AddMsg(tmplist.get(i));
		}
		buf.release();
	}

}
