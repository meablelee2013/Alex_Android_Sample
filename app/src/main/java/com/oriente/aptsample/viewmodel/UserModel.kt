package com.oriente.aptsample.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.common.AppJoint
import com.example.common.BorrowAction
import com.example.common.UserAction

class UserModel(application: Application) : AndroidViewModel(application) {

    var context: Context = application
    var name = MutableLiveData("Ada")
    private var _lastName = MutableLiveData("Lovelace")
    var age =
        MutableLiveData(1)//不是string的字段，在xml里要toString(),否则会crash      android:text="@{userModel.age.toString()}"

    fun nextPage() {
//        var intent = Intent(context, SecondActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        context.startActivity(intent)
//        val service = AutoServiceUtil2.getInstance().getService(BorrowAction::class.java)
//        service.borrow()
//        service.verifyBorrow()

//        val paymentAction = AutoServiceUtil2.getInstance().getService(PaymentAction::class.java)
//        paymentAction.payment()

        val borrow = AppJoint.getInstance().getService(BorrowAction::class.java)
        borrow.borrow()
        borrow.verifyBorrow()

        val userAction = AppJoint.getInstance().getService(UserAction::class.java)
        userAction.getUserName()


    }

}