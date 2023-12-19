package com.ruma.payment

import com.example.common.PaymentAction
import com.google.auto.service.AutoService

@AutoService(PaymentAction::class)
class PaymentActionImpl : PaymentAction {
    override fun payment() {

    }

    override fun name(): String {

        return "PaymentActionImpl"
    }
}