package cl.edenprime.survivor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import cl.edenprime.survivor.obj.ThemeSwitcher;
 
public class WebActivity extends Activity {
 

    private WebView browser;
    private ProgressBar progressBar;

 
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ThemeSwitcher.setTheme(this);
        setContentView(R.layout.web);
 
        browser = (WebView) findViewById(R.id.webview);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl("http://promocion.inf.utfsm.cl/");
        browser.getSettings().setBuiltInZoomControls(true);
        browser.getSettings().setSupportZoom(true);
        browser.getSettings().setDefaultZoom(ZoomDensity.FAR);
        browser.setWebViewClient(new WebBrowserClient());
        
        progressBar = (ProgressBar) findViewById(R.id.progressbar);           
        
        browser.setWebChromeClient(new WebChromeClient(){
        	@Override
        	public void onProgressChanged(WebView view, int progress){
        		progressBar.setProgress(0);
        		progressBar.setVisibility(View.VISIBLE);
        		WebActivity.this.setProgress(progress * 1000);
        		progressBar.incrementProgressBy(progress);
                if(progress == 100){
                	progressBar.setVisibility(View.GONE);
                }
        	}
        });
        
    }
    
    private class WebBrowserClient extends WebViewClient {
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView browser, String url){
    		browser.loadUrl(url);
    		return true;
    	}
    }
    
    public void ShowUsmWebFavorites(View v) {
    	openFavoriteWebDialog();
	}
    
    private void openFavoriteWebDialog() {
		new AlertDialog.Builder(this)
		.setTitle("USM Favoritos")
		.setItems(R.array.usmWeb,
		new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialoginterface,int opt) {
			switch (opt){
				case 0:	{
					browser.loadUrl("http://promocion.inf.utfsm.cl/");
					}
					break;	
				case 1:	{
					browser.loadUrl("http://www.centrodealumnos.cl");
					}
				break;
				case 2:	{
					browser.loadUrl("http://www.inf.utfsm.cl/");
				}
//				break;
//				case 3:	{
//					browser.loadUrl("https://moodle.inf.utfsm.cl/login/index.php");
//				}
//				break; 
//				case 4: {
//					browser.loadUrl("https://www.siga.usm.cl/pag/");
//				}
			}
		}
		})
		.show();
	}
    
}