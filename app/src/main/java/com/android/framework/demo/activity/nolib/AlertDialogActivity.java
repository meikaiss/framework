package com.android.framework.demo.activity.nolib;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.framework.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 16/1/4.
 */
public class AlertDialogActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_01: {
                android.app.AlertDialog.Builder builder0 = new android.app.AlertDialog.Builder(this);
                builder0.setTitle("系统弹框测试").setCancelable(true).setMessage("message显示的内容").setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(AlertDialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "点击" + which, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("否定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "否定" + which, Toast.LENGTH_SHORT).show();
                    }
                }).setNeutralButton("中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "中立" + which, Toast.LENGTH_SHORT).show();
                    }
                });
                builder0.create().show();
            }
            break;
            case R.id.btn_02: {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
                builder.setTitle("系统弹框测试").setCancelable(true).setMessage("message显示的内容").setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(AlertDialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "点击" + which, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("否定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "否定" + which, Toast.LENGTH_SHORT).show();
                    }
                }).setNeutralButton("中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "中立" + which, Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
            }
            break;
            case R.id.btn_03: {
                android.app.AlertDialog.Builder builder0 = new android.app.AlertDialog.Builder(this);
                builder0.setView(new EditText(this)).
                        setTitle("系统弹框测试").setCancelable(true).setMessage("message显示的内容").setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(AlertDialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "点击" + which, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("否定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "否定" + which, Toast.LENGTH_SHORT).show();
                    }
                }).setNeutralButton("中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "中立" + which, Toast.LENGTH_SHORT).show();
                    }
                });
                builder0.create().show();
            }
            break;
            case R.id.btn_04: {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
                builder.setView(new EditText(this)).
                        setTitle("系统弹框测试").setCancelable(true).setMessage("message显示的内容").setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(AlertDialogActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "点击" + which, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("否定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "否定" + which, Toast.LENGTH_SHORT).show();
                    }
                }).setNeutralButton("中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogActivity.this, "中立" + which, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }
            break;
            case R.id.btn_05:
                new android.app.AlertDialog.Builder(this).setTitle("列表框").setItems(
                        new String[]{"Item1", "Item2", "Item3", "Item4", "Item5"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogActivity.this, "第" + which + "项被点击", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton(
                        "确定", null).show();
                break;
            case R.id.btn_06:
                new AlertDialog.Builder(this).setTitle("列表框").setItems(
                        new String[]{"Item1", "Item2", "Item3", "Item4", "Item5"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogActivity.this, "第" + which + "项被点击", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton(
                        "确定", null).show();
                break;
            case R.id.btn_07:
                new android.app.AlertDialog.Builder(this).setTitle("单选框").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"Item1", "Item2", "Item3", "Item4", "Item5"}, 1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogActivity.this, "第" + which + "项被点击", Toast.LENGTH_SHORT).show();
                            }
                        }).setPositiveButton("确定", null).setNegativeButton("取消", null).show();
                break;
            case R.id.btn_08:
                new AlertDialog.Builder(this).setTitle("单选框").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"Item1", "Item2", "Item3", "Item4", "Item5"}, 1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AlertDialogActivity.this, "第" + which + "项被点击", Toast.LENGTH_SHORT).show();
                            }
                        }).setPositiveButton("确定", null).setNegativeButton("取消", null).show();
                break;
            case R.id.btn_09:

                new android.app.AlertDialog.Builder(this).setTitle("复选框").setMultiChoiceItems(
                        new String[]{"Item1", "Item2", "Item3", "Item4", "Item5"}, null, null)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null).show();
                break;
            case R.id.btn_10:
                new AlertDialog.Builder(this).setTitle("复选框").setMultiChoiceItems(
                        new String[]{"Item1", "Item2", "Item3", "Item4", "Item5"}, null, null)
                        .setPositiveButton("确定", null)
                        .setNegativeButton("取消", null).show();
                break;
            case R.id.btn_11: {

                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
                ListView dialogList = (ListView) dialogView.findViewById(R.id.dialog_list);
                List<String> dataList = new ArrayList<>();
                dataList.add("选项1");
                dataList.add("选项2");
                dataList.add("选项3");
                dataList.add("选项4");
                dataList.add("选项5");
                dialogList.setAdapter(new ArrayAdapter<>(this, R.layout.item_demo, R.id.tv_item_name, dataList));

                new android.app.AlertDialog.Builder(this).setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNeutralButton("中立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
            break;
            case R.id.btn_12: {

                View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
                ListView dialogList = (ListView) dialogView.findViewById(R.id.dialog_list);
                List<String> dataList = new ArrayList<>();
                dataList.add("选项1");
                dataList.add("选项2");
                dataList.add("选项3");
                dataList.add("选项4");
                dataList.add("选项5");
                dialogList.setAdapter(new ArrayAdapter<>(this, R.layout.item_demo, R.id.tv_item_name, dataList));

                new AlertDialog.Builder(this).setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNeutralButton("中立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
            }
            break;
            default:
                break;
        }
    }
}
