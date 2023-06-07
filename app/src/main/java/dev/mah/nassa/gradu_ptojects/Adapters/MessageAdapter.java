package dev.mah.nassa.gradu_ptojects.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dev.mah.nassa.gradu_ptojects.Modles.MessagesModles;
import dev.mah.nassa.gradu_ptojects.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<MessagesModles> messagesModlesList;
    private Context context;
    String loadId;

    public MessageAdapter(Context context , String loadId){
        this.context=context;
        this.loadId=loadId;
        messagesModlesList=new ArrayList<>();
    }

    public void addMessage(MessagesModles messagesModles){
        messagesModlesList.add(0 , messagesModles);
        notifyItemInserted(0);
    }

    public void clearAdapter(){
        messagesModlesList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view =   LayoutInflater.from(context).inflate(R.layout.message_design2 , parent , false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

       MessagesModles messagesModles =  messagesModlesList.get(position);

        // صورة المرسل
        if(messagesModles.getPhotoUrl() == null || messagesModles.getPhotoUrl().isEmpty()){
            holder.senderPhotot.setImageDrawable(context.getDrawable(R.drawable.user));
        }else {
            Glide.with(context).load(messagesModles.getPhotoUrl()).into(holder.senderPhotot);
        }


        if(messagesModles.getMessagePhoto() != null || !messagesModles.getMessagePhoto().isEmpty()){
            Glide.with(context).load(messagesModles.getMessagePhoto()).into(holder.messagePhoto);
            holder.messagePhoto.setMaxHeight(200);
        }


        holder.timeTv.setText(messagesModles.getMsgTime());
        holder.nameTv.setText(messagesModles.getSenderName());
        holder.messageText.setText(messagesModles.getMessageText());

        //  ميثود لمعرفة المرسل و التحكم في اتجاه الرسالة يمين و يسار
       if(messagesModles.getSenderId().equals(loadId)){
           holder.parentLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
           holder.childParentLayout.setBackground(context.getDrawable(R.drawable.blue_chat_backgroun));
       }else {
           holder.parentLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
           holder.childParentLayout.setBackground(context.getDrawable(R.drawable.grey_chat_background));
       }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context , holder.itemView);
                popupMenu.getMenuInflater().inflate(R.menu.drop_menu , popupMenu.getMenu());
                setForceShowIcon(popupMenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:

                                try {
                                    // الحذف من طرف المرسل
                                    FirebaseDatabase.getInstance().getReference("Chat Messages")
                                            .child(messagesModles.getSenderId()+messagesModles.getReciverId()).child(messagesModles.getDocumentMessgeId())
                                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }catch (Exception e){
                                    Toast.makeText(context, e.getMessage().toString()+"", Toast.LENGTH_SHORT).show();
                                }


                                try {
                                    // الحذف من طرف المستقبل
                                    FirebaseDatabase.getInstance().getReference("Chat Messages")
                                            .child(messagesModles.getReciverId()+messagesModles.getSenderId()).child(messagesModles.getDocumentMessgeId())
                                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }catch (Exception e){
                                    Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }


                                return true;

                            default:
                                return false;

                        }
                    }
                });


                popupMenu.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return messagesModlesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public ImageView messagePhoto;
        public CircularImageView senderPhotot;
        public TextView messageText , timeTv , nameTv ;
        public LinearLayout   childParentLayout;
        public ConstraintLayout parentLayout;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            childParentLayout = itemView.findViewById(R.id.childParentLayout);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            senderPhotot = itemView.findViewById(R.id.messageUsersImage);
            messagePhoto = itemView.findViewById(R.id.messageImage);
            messageText = itemView.findViewById(R.id.messageTv);
            nameTv = itemView.findViewById(R.id.nameTv);
            timeTv = itemView.findViewById(R.id.timeTv);
        }
    }

    //Context Menu ميثود لعرض الأيقونات في
    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] mFields = popupMenu.getClass().getDeclaredFields();
            for (Field field : mFields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> popupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method mMethods = popupHelper.getMethod("setForceShowIcon", boolean.class);
                    mMethods.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

