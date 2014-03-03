using System;

namespace Emotracker.Core
{
	public class MessageConverter
	{
		public static OperationResult fromWebMessage(WebMessage message) 
		{
			OperationResult res = new OperationResult ();

			res.Content = message.Result;
			res.Message = message.Message;

			if (message.State == WebMessage.OK_RESULT) {
				res.Result = true;
			} else if (message.State == WebMessage.ERROR_RESULT) {
				res.Result = false;
			}

			return res;
		}
	}
}

