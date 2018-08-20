package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sdsmdg.tastytoast.TastyToast;

import java.net.URISyntaxException;

public class InicisWebViewClient extends WebViewClient {
	
	private Activity activity;
	
	public InicisWebViewClient(Activity activity) {
		this.activity = activity;
	}


	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {

		Log.d("InicisWebViewClient", "1");
		Log.d("InicisWebViewClient", url);

		if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {

			Intent intent = null;

			try {

				intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
				Uri uri = Uri.parse(intent.getDataString());

				activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
				return true;

			} catch (URISyntaxException ex) {

				return false;

			} catch (ActivityNotFoundException e) {

				if ( intent == null )	return false;

				if ( handleNotFoundPaymentScheme(intent.getScheme()) )	return true;

				String packageName = intent.getPackage();

				if (packageName != null) {

					Log.d("packageName", packageName);

					activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
					return true;

				}

				Log.d("InicisWebViewClient", "return false");

				return false;
			}

		} else {

			if(url.equals("https://pg-app-q0nz5d8azjwlxxrweewghhzuexr7rc.scalabl.cloud/payments/complete/success")){

				TastyToast.makeText(activity, "결제가 완료되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

				activity.finish();

				return true;

			} else if(url.equals("https://pg-app-q0nz5d8azjwlxxrweewghhzuexr7rc.scalabl.cloud/payments/complete/fail")){

				TastyToast.makeText(activity, "결제는 완료되었으나, 지급 에러가 발생했습니다. 에러 신고가 되었으니 잠시만 기다려 주세요. 확인 후 메시지 드리겠습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

				activity.finish();

				return true;

			} else if(url.equals("https://pg-app-q0nz5d8azjwlxxrweewghhzuexr7rc.scalabl.cloud/payments/fail")){

				TastyToast.makeText(activity, "결제가 취소 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

				activity.finish();

				return true;

			} else if(url.contains("https://pg-app-q0nz5d8azjwlxxrweewghhzuexr7rc.scalabl.cloud/payments/complete")){

				return false;

			}

			return false;
		}

	}
	
	/**
	 * @param scheme
	 * @return 해당 scheme에 대해 처리를 직접 하는지 여부
	 * 
	 * 결제를 위한 3rd-party 앱이 아직 설치되어있지 않아 ActivityNotFoundException이 발생하는 경우 처리합니다.
	 * 여기서 handler되지않은 scheme에 대해서는 intent로부터 Package정보 추출이 가능하다면 다음에서 packageName으로 market이동합니다.  
	 * 
	 */
	protected boolean handleNotFoundPaymentScheme(String scheme) {

		Log.d("InicisWebViewClient", "2");
		Log.d("InicisWebViewClient", scheme);

		//PG사에서 호출하는 url에 package정보가 없어 ActivityNotFoundException이 난 후 market 실행이 안되는 경우

		if ( PaymentScheme.ISP.equalsIgnoreCase(scheme) ) {
			activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_ISP)));
			return true;
		} else if ( PaymentScheme.BANKPAY.equalsIgnoreCase(scheme) ) {
			activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_BANKPAY)));
			return true;
		}

		return false;
	}


}
