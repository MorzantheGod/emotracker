using System;

namespace Emotracker.Core
{
	public class RegistrationService
	{
		public RegistrationService ()
		{
		}

		public static OperationResult registerNewUser(RegistrationDTO dto) {
			return new OperationResult(true, "OK");
		}
	}
}

