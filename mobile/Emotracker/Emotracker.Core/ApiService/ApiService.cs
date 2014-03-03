using System;
using RestSharp;
using System.Collections.Generic;
using Newtonsoft.Json;
using System.Net;

namespace Emotracker.Core
{
	public abstract class ApiService
	{
		private static readonly String BASE_URL = "http://localhost:8080/api/";

		public WebMessage sendPostRequest(String url) 
		{
			return sendRequest (url, Method.POST, null);
		}

		public WebMessage sendPostRequest(String url, Dictionary<string, Object> param)
		{
			return sendRequest (url, Method.POST, param);
		}

		public WebMessage sendGetRequest(String url, Dictionary<string, Object> param)
		{
			return sendRequest (url, Method.GET, param);
		}

		public WebMessage sendRequest(String url, Method method, Dictionary<string, Object> param)
		{
			var client = new RestClient (BASE_URL);
			var request = new RestRequest (buildUrl(url), method);

			if (param != null) {
				foreach(KeyValuePair<string, Object> item in param)
				{
					request.AddParameter (item.Key, item.Value);
				}
			}


			IRestResponse response = client.Execute (request);
			WebException e = response.ErrorException as WebException;
			if (e != null) {
				Console.WriteLine (e.Status);

				return getErrorWebMessage ();
			}

			WebMessage ans;
			try {
				ans = JsonConvert.DeserializeObject<WebMessage> (response.Content);
			}
			catch( Exception ex ) {
				Console.WriteLine (ex.Message);
				return getErrorWebMessage ();
			}

			return ans;
		}

		private WebMessage getErrorWebMessage() {
			WebMessage mes = new WebMessage ();
			mes.State = WebMessage.ERROR_RESULT;
			mes.Message = "Sorry, some problems with a server";
			return mes;
		}

		private string buildUrl(string url)
		{
			string res = getCurrentApiUrl ();

			if (url != null) {
				if (url.Length > 0) {
					char c = url [0];
					if (c != '/') {
						res += "/";
					}
				}

				res += url;
			}

			return res;
		}

		protected abstract string getCurrentApiUrl();
	}
}

