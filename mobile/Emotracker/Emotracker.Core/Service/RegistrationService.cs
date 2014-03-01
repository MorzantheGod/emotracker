using System;
using RestSharp;
using Newtonsoft.Json;
using PerpetualEngine.Storage;

namespace Emotracker.Core
{
	public class RegistrationService
	{
		private UserApiService userApiService = new UserApiService ();
	
		public OperationResult registerNewUser(RegistrationDTO dto) {

			WebMessage mes = userApiService.createNewUser (dto);

			OperationResult res = MessageConverter.fromWebMessage (mes);
			return res;
		}
	}
}

