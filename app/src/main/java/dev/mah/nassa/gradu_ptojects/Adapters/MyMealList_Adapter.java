package dev.mah.nassa.gradu_ptojects.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.mah.nassa.gradu_ptojects.Constants.LanguageUtils;
import dev.mah.nassa.gradu_ptojects.DataBase.FireStore_DataBase;
import dev.mah.nassa.gradu_ptojects.MVVM.My_Meal_MVVM;
import dev.mah.nassa.gradu_ptojects.Modles.My_Meal_List;
import dev.mah.nassa.gradu_ptojects.R;

public class MyMealList_Adapter extends RecyclerView.Adapter<MyMealList_Adapter.MyViewHolder>{

    private ArrayList<My_Meal_List>list;
    private boolean dropDown=false;
    Activity activity;
    TextView tvEmpty;
    Toolbar toolbar;
    My_Meal_MVVM mainViewModel;
    boolean isEnable=false;
    boolean isSelectAll=false;
    ArrayList<My_Meal_List> selectList=new ArrayList<>();
    private ArrayList<My_Meal_List> copyMy_meal_lists;
    private Context context;


    // create constructor
    public MyMealList_Adapter(Activity activity,ArrayList<My_Meal_List> list,TextView tvEmpty,Toolbar toolbar , Context context)
    {
        this.activity=activity;
        this.list=list;
        this.copyMy_meal_lists = list;
        this.tvEmpty=tvEmpty;
        this.toolbar = toolbar;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_meal_list_recyclerview_items, parent, false);


        // initialize view Model
        mainViewModel= ViewModelProviders.of((FragmentActivity) activity)
                .get(My_Meal_MVVM.class);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        LanguageUtils.changeLanguage(context , "en");

        My_Meal_List meal = list.get(position);
        holder.mealName.setText(meal.getNameMeal());
        holder.mealCalories.setText(meal.getCaloriesMeal());
        holder.mealWeightMeal.setText(meal.getWeight());
        holder.mealDATE.setText(meal.getDate());
        holder.mealTime.setText(meal.getTime());


        ViewGroup.LayoutParams layoutParams =  holder.tableLayout.getLayoutParams();
        if((position+1) != list.size()){
            layoutParams.height=0;
            holder.tableLayout.setLayoutParams(layoutParams);
            holder.iconDrop.setRotation(360);
        }else {
            layoutParams.height=-2;
            holder.tableLayout.setLayoutParams(layoutParams);
            holder.iconDrop.setRotation(270);
        }

        holder.iconDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dropDown==false){
                    layoutParams.height=0;
                    holder.iconDrop.setRotation(360);
                    holder.tableLayout.setLayoutParams(layoutParams);
                    dropDown=true;
                }else {
                    dropDown=false;
                    layoutParams.height=-2;
                    holder.iconDrop.setRotation(270);
                    holder.tableLayout.setLayoutParams(layoutParams);

                }
            }
        });

        //عند ضغط ضغطة مطولة
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // check condition
                if (!isEnable)
                {
                    // when action mode is not enable
                    // initialize action mode
                    ActionMode.Callback callback=new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            toolbar.setVisibility(View.GONE);
                            // initialize menu inflater
                            MenuInflater menuInflater= mode.getMenuInflater();
                            // inflate menu
                            menuInflater.inflate(R.menu.click_menu,menu);
                            // return true

                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            // when action mode is prepare
                            // set isEnable true
                            isEnable=true;
                            // create method
                            ClickItem(holder);
                            // set observer on getText method
                            mainViewModel.getTitle().observe((LifecycleOwner) activity
                                    , new Observer<String>() {
                                        @Override
                                        public void onChanged(String s) {
                                            // when text change
                                            // set text on action mode title
                                            mode.setTitle(String.format(s,"%s تم تحديد"));

                                        }
                                    });
                            // return true
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            // when click on action mode item
                            // get item  id
                            int id=item.getItemId();
                            // use switch condition
                            switch(id)
                            {
                                case R.id.menu_delete:
                                    // when click on delete
                                    // use for loop
                                    for(My_Meal_List s:selectList)
                                    {
                                        // remove selected item list
                                        mainViewModel.deleteMy_Meal_List(s);
                                        FireStore_DataBase.deleteMyMealList(s , context);
                                        list.remove(s);
                                    }
                                    // check condition
                                    if(list.size()==0)
                                    {
                                        // when array list is empty
                                        // visible text view
//                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                    // finish action mode
                                    mode.finish();
                                    break;

                                case R.id.menu_select_all:
                                    // when click on select all
                                    // check condition
                                    if(selectList.size()==list.size())
                                    {
                                        // when all item selected
                                        // set isselectall false
                                        isSelectAll=false;
                                        // create select array list
                                        selectList.clear();
                                    }
                                    else
                                    {
                                        // when  all item unselected
                                        // set isSelectALL true
                                        isSelectAll=true;
                                        // clear select array list
                                        selectList.clear();
                                        // add value in select array list
                                        selectList.addAll(list);
                                    }
                                    // set text on view model
                                    mainViewModel.setTitle(String .valueOf(selectList.size()));
                                    // notify adapter
                                    notifyDataSetChanged();
                                    break;
                            }
                            // return true
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            toolbar.setVisibility(View.VISIBLE);
                            // when action mode is destroy
                            // set isEnable false
                            isEnable=false;
                            // set isSelectAll false
                            isSelectAll=false;
                            // clear select array list
                            selectList.clear();
                            // notify adapter
                            notifyDataSetChanged();
                        }
                    };
                    // start action mode
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
                }
                else
                {
                    // when action mode is already enable
                    // call method
                    ClickItem(holder);
                }
                // return true
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check condition
                if(isEnable)
                {
                    // when action mode is enable
                    // call method
                    ClickItem(holder);
                }
                else
                {
                    // when action mode is not enable
                    // display toast
//                    Toast.makeText(activity,"You Clicked"+list.get(holder.getAdapterPosition()),
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // check condition
        if(isSelectAll)
        {
            // when value selected
            // visible all check boc image
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            //set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        else
        {
            // when all value unselected
            // hide all check box image
            holder.ivCheckBox.setVisibility(View.GONE);
            // set background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

    }
    private void ClickItem(MyViewHolder holder) {

        // get selected item value
        My_Meal_List s=list.get(holder.getAdapterPosition());
        // check condition
        if(holder.ivCheckBox.getVisibility()==View.GONE)
        {
            // when item not selected
            // visible check box image
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            // set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            // add value in select array list
            selectList.add(s);
        }
        else
        {
            // when item selected
            // hide check box image
            holder.ivCheckBox.setVisibility(View.GONE);
            // set background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            // remove value from select arrayList
            selectList.remove(s);

        }
        // set text on view model
        mainViewModel.setTitle(String.valueOf(selectList.size()));
    }

    //ميثود البحث لعمل فلترة اثناء ستخدام البحث
    public Filter getFilter() {

        Filter myFilter=new Filter() {
            FilterResults fr=new FilterResults();
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                ArrayList<My_Meal_List> temp=new ArrayList<My_Meal_List>();
                for(int i=0;i<copyMy_meal_lists.size();i++){
                    My_Meal_List c=copyMy_meal_lists.get(i);
                    String stno=c.getDate()+"";
                    if(c.getNameMeal().toLowerCase().contains(charSequence.toString().toLowerCase()) || stno.contains(charSequence.toString()) ){
                        temp.add(c);
                    }
                }
                fr.values=temp;
                fr.count=temp.size();

                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults fr) {
                list=(ArrayList<My_Meal_List>) fr.values;
                notifyDataSetChanged();
            }
        };

        return myFilter;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mealName,mealCalories,mealWeightMeal,mealTime,mealDATE;
        public TableLayout tableLayout;
        public LinearLayoutCompat linearLayout;
        public ImageView iconDrop,ivCheckBox,deleteButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.myMealList_RV_tv_name);
            mealCalories = itemView.findViewById(R.id.myMealList_RV_tv_calories);
            mealWeightMeal = itemView.findViewById(R.id.myMealList_RV_tv_weightMeal);
            mealDATE = itemView.findViewById(R.id.myMealList_RV_dateTv);
            mealTime = itemView.findViewById(R.id.myMealList_RV_tv_time);
            tableLayout = itemView.findViewById(R.id.myMealList_RV_tb_tableDetails);
            linearLayout = itemView.findViewById(R.id.meal_linear);
            iconDrop = itemView.findViewById(R.id.myMealList_RV_im_dropIcon);
            ivCheckBox = itemView.findViewById(R.id.myMealList_check_box);
        }
    }

}
