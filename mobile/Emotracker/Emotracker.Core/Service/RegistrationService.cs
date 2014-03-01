using System;
using RestSharp;
using Newtonsoft.Json;

namespace Emotracker.Core
{
	public class RegistrationService
	{
		private UserApiService userApiService = new UserApiService ();
		private TokenApiService tokenApiService = new TokenApiService();
	
		public OperationResult registerNewUser(RegistrationDTO dto) {

			WebMessage token = tokenApiService.createNewToken ();

			TokenDTO tokenDTO = token.Result as TokenDTO;
			dto.TokenId = tokenDTO.Id;
			dto.Key = tokenDTO.Key;
			dto.Token = tokenDTO.Token;

			WebMessage mes = userApiService.createNewUser (dto);

			OperationResult res = MessageConverter.fromWebMessage (mes);
			return res;
		}
	}
}

