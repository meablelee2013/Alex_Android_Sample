package com.ruma.payment

import com.example.common.BaseAction
import com.example.common.PaymentAction
import com.google.auto.service.AutoService

@AutoService(BaseAction::class)
class PaymentActionImpl : PaymentAction {
    override fun payment() {
        println("PaymentActionImpl.payment")
    }

    override fun name(): String {
        return "PaymentActionImpl"
    }
}