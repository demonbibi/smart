package teacher.test2;

import java.util.ArrayList;
import java.util.List;


import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piano.R;

public class TreeView extends ListActivity {
	private ArrayList<PDFOutlineElement> mPdfOutlinesCount = new ArrayList<PDFOutlineElement>();
	private ArrayList<PDFOutlineElement> mPdfOutlines = new ArrayList<PDFOutlineElement>();
	private TreeViewAdapter treeViewAdapter = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initialData();
        treeViewAdapter = new TreeViewAdapter(this, R.layout.outline,
				mPdfOutlinesCount);
		setListAdapter(treeViewAdapter);
		registerForContextMenu(getListView());
    }   
    
    private void initialData() {
		PDFOutlineElement pdfOutlineElement1=new PDFOutlineElement("01", "关键类", false	, false, "00", 0,false);
		PDFOutlineElement pdfOutlineElement2=new PDFOutlineElement("02", "应用程序组件", false	, true, "00", 0,false);
		PDFOutlineElement pdfOutlineElement3=new PDFOutlineElement("03", "Activity和任务", false	, true, "00", 0,false);
		PDFOutlineElement pdfOutlineElement4=new PDFOutlineElement("04", "激活组件：intent", true	, false, "02", 1,false);
		PDFOutlineElement pdfOutlineElement5=new PDFOutlineElement("05", "关闭组件", true	, false, "02", 1,false);
		PDFOutlineElement pdfOutlineElement6=new PDFOutlineElement("06", "manifest文件", true	, false, "02", 1,false);
		PDFOutlineElement pdfOutlineElement7=new PDFOutlineElement("07", "Intent过滤器", true	, false, "02", 1,false);
		PDFOutlineElement pdfOutlineElement8=new PDFOutlineElement("08", "Affinity（吸引力）和新任务", true	, false, "03", 1,false);
		PDFOutlineElement pdfOutlineElement9=new PDFOutlineElement("09", "加载模式", true	, true, "03", 1,false);
		PDFOutlineElement pdfOutlineElement10=new PDFOutlineElement("10", "加载模式孩子1", true	, true, "09", 2,false);
		PDFOutlineElement pdfOutlineElement11=new PDFOutlineElement("11", "加载模式孩子2", true	, true, "09", 2,false);
		PDFOutlineElement pdfOutlineElement12=new PDFOutlineElement("12", "加载模式孩子2的孩子1", true	, false, "11", 3,false);
		PDFOutlineElement pdfOutlineElement13=new PDFOutlineElement("13", "加载模式孩子2的孩子2", true	, false, "11", 3,false);
		PDFOutlineElement pdfOutlineElement14=new PDFOutlineElement("14", "加载模式孩子1的孩子1", true	, false, "10", 3,false);
		PDFOutlineElement pdfOutlineElement15=new PDFOutlineElement("15", "加载模式孩子1的孩子2", true	, false, "10", 3,false);
		PDFOutlineElement pdfOutlineElement16=new PDFOutlineElement("16", "加载模式孩子1的孩子3", true	, false, "10", 3,false);
		PDFOutlineElement pdfOutlineElement17=new PDFOutlineElement("17", "加载模式孩子1的孩子4", true	, false, "10", 3,false);
		PDFOutlineElement pdfOutlineElement18=new PDFOutlineElement("18", "加载模式孩子1的孩子5", true	, false, "10", 3,false);
		PDFOutlineElement pdfOutlineElement19=new PDFOutlineElement("19", "加载模式孩子1的孩子6", true	, false, "10", 3,false);
		mPdfOutlinesCount.add(pdfOutlineElement1);
		mPdfOutlinesCount.add(pdfOutlineElement2);
		mPdfOutlinesCount.add(pdfOutlineElement3);
	
		
		mPdfOutlines.add(pdfOutlineElement1);
		mPdfOutlines.add(pdfOutlineElement2);
		mPdfOutlines.add(pdfOutlineElement4);
		mPdfOutlines.add(pdfOutlineElement5);
		mPdfOutlines.add(pdfOutlineElement6);
		mPdfOutlines.add(pdfOutlineElement7);
		mPdfOutlines.add(pdfOutlineElement3);
		mPdfOutlines.add(pdfOutlineElement8);
		mPdfOutlines.add(pdfOutlineElement9);
		mPdfOutlines.add(pdfOutlineElement10);
		mPdfOutlines.add(pdfOutlineElement11);
		mPdfOutlines.add(pdfOutlineElement12);
		mPdfOutlines.add(pdfOutlineElement13);
		mPdfOutlines.add(pdfOutlineElement14);
		mPdfOutlines.add(pdfOutlineElement15);
		mPdfOutlines.add(pdfOutlineElement16);
		mPdfOutlines.add(pdfOutlineElement17);
		mPdfOutlines.add(pdfOutlineElement18);
		mPdfOutlines.add(pdfOutlineElement19);
		
		
		
	}

	private class TreeViewAdapter extends ArrayAdapter {

		public TreeViewAdapter(Context context, int textViewResourceId,
				List objects) {
			super(context, textViewResourceId, objects);
			mInflater = LayoutInflater.from(context);
			mFileList = objects;
			mIconCollapse = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.outline_list_collapse);
			mIconExpand = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.outline_list_expand);

		}

		private LayoutInflater mInflater;
		private List<PDFOutlineElement> mFileList;
		private Bitmap mIconCollapse;
		private Bitmap mIconExpand;


		public int getCount() {
			return mFileList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.outline, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.text);
				holder.icon = (ImageView) convertView.findViewById(R.id.icon);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			int level = mFileList.get(position).getLevel();
 			holder.icon.setPadding(25 * (level + 1), holder.icon
					.getPaddingTop(), 0, holder.icon.getPaddingBottom());
			holder.text.setText(mFileList.get(position).getOutlineTitle());
			if (mFileList.get(position).isMhasChild()
					&& (mFileList.get(position).isExpanded() == false)) {
				holder.icon.setImageBitmap(mIconCollapse);
			} else if (mFileList.get(position).isMhasChild()
					&& (mFileList.get(position).isExpanded() == true)) {
				holder.icon.setImageBitmap(mIconExpand);
			} else if (!mFileList.get(position).isMhasChild()){
				holder.icon.setImageBitmap(mIconCollapse);
				holder.icon.setVisibility(View.INVISIBLE);
			}
			return convertView;
		}

		class ViewHolder {
			TextView text;
			ImageView icon;

		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if (!mPdfOutlinesCount.get(position).isMhasChild()) {
			Toast.makeText(this, mPdfOutlinesCount.get(position).getOutlineTitle(), Toast.LENGTH_SHORT);
			/*int pageNumber;
			Intent i = getIntent();
			PDFOutlineElement element = mPdfOutlinesCount
					.get(position);
			pageNumber = element.getOutlineElement().pageNumber;
			if (pageNumber <= 0) {
				String name = element.getOutlineElement().destName;
				pageNumber = idocviewer.getPageNumberForNameForOutline(name);
				element.getOutlineElement().pageNumber = pageNumber;
				element.getOutlineElement().destName = null;
			}
			i.putExtra("PageNumber", pageNumber);
			setResult(RESULT_OK, i);
			finish();*/

			return;
		}
		

		if (mPdfOutlinesCount.get(position).isExpanded()) {
			mPdfOutlinesCount.get(position).setExpanded(false);
			PDFOutlineElement pdfOutlineElement=mPdfOutlinesCount.get(position);
			ArrayList<PDFOutlineElement> temp=new ArrayList<PDFOutlineElement>();
			
			for (int i = position+1; i < mPdfOutlinesCount.size(); i++) {
				if (pdfOutlineElement.getLevel()>=mPdfOutlinesCount.get(i).getLevel()) {
					break;
				}
				temp.add(mPdfOutlinesCount.get(i));
			}
			
			mPdfOutlinesCount.removeAll(temp);
			
			treeViewAdapter.notifyDataSetChanged();
			/*fileExploreAdapter = new TreeViewAdapter(this, R.layout.outline,
					mPdfOutlinesCount);*/

			//setListAdapter(fileExploreAdapter);
			
		} else {
			mPdfOutlinesCount.get(position).setExpanded(true);
			int level = mPdfOutlinesCount.get(position).getLevel();
			int nextLevel = level + 1;
			

			for (PDFOutlineElement pdfOutlineElement : mPdfOutlines) {
				int j=1;
				if (pdfOutlineElement.getParent()==mPdfOutlinesCount.get(position).getId()) {
					pdfOutlineElement.setLevel(nextLevel);
					pdfOutlineElement.setExpanded(false);
					mPdfOutlinesCount.add(position+j, pdfOutlineElement);
					j++;
				}			
			}
			treeViewAdapter.notifyDataSetChanged();
			/*fileExploreAdapter = new TreeViewAdapter(this, R.layout.outline,
					mPdfOutlinesCount);*/

			//setListAdapter(fileExploreAdapter);
		}
	}

}