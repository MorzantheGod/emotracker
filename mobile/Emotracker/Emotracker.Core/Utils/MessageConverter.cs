using System;

namespace Emotracker.Core
{
	public class MessageConverter
	{
		public static readonly string OK_RESULT = "OK";
		public static readonly string ERROR_RESULT = "ERROR";

		public static OperationResult fromWebMessage(WebMessage message) 
		{
			OperationResult res = new OperationResult ();

			res.Content = message.Result;
			res.Message = message.Message;

			if (message.State == OK_RESULT) {
				res.Result = true;
			} else if (message.State == ERROR_RESULT) {
				res.Result = false;
			}

			return res;
		}
	}
}

