using System;
using Newtonsoft.Json;

namespace Emotracker.Core
{
	public class TokenApiService : ApiService
	{
		public static readonly String THIS_API = "tokens";
		public static readonly String CREATE_TOKEN = "create";

		public WebMessage createNewToken() {
			WebMessage res = this.sendPostRequest (CREATE_TOKEN);

			Object result = res.Result;
			if (result != null) {
				TokenDTO token = JsonConvert.DeserializeObject<TokenDTO> (result.ToString ());
				res.Result = token;
			}

			return res;
		}

		protected override string getCurrentApiUrl()
		{
			return THIS_API;
		}
	}


}

