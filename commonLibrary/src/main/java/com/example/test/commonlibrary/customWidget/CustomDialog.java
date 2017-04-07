package com.example.test.commonlibrary.customWidget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dykj.baselibrary.R;


public class CustomDialog extends Dialog {
	
	public CustomDialog(Context context) {
		super(context);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;

		public Builder(Context context) {
			this.context = context;
		}

		
		public CustomDialog create(View layout,int[] ids,final OnClickListener listener) {
			// instantiate the dialog with the custom Theme
			final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
//			dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			
			if(listener != null){
				for(int id : ids){
					layout.findViewById(id).setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							listener.onClick(dialog, v.getId());
						}
					});
				}
			}
			dialog.setContentView(layout);
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//			dialog.setContentView(R.layout.tag_add);
			return dialog;
		}

        public CustomDialog create(int layoutId,int[] ids,final OnClickListener listener) {
            // instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            View layout = LayoutInflater.from(context).inflate(layoutId, null);
//			dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            if(listener != null){
                for(int id : ids){
                    layout.findViewById(id).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            listener.onClick(dialog, v.getId());
                        }
                    });
                }
            }
            dialog.setContentView(layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//			dialog.setContentView(R.layout.tag_add);
            return dialog;
        }


        public CustomDialog create(int layoutId){
			final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
			dialog.setContentView(layoutId);
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			return dialog;
		}

		public CustomDialog create(View layout){
			final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
			dialog.setContentView(layout);
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			return dialog;
		}

	}
}
