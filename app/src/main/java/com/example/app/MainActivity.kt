package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.core.utils.toast
import com.example.lesson.LessonActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val usernameKey = "username"
    private val passwordKey = "password"

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etCode: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        etCode = findViewById(R.id.et_code)

        etUsername.setText(CacheUtils[usernameKey])
        etPassword.setText(CacheUtils[passwordKey])

        findViewById<Button>(R.id.btn_login).setOnClickListener(this)
        findViewById<CodeView>(R.id.code_view).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            is CodeView -> v.updateCode()
            is Button -> login()
        }
    }

    private fun login() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val code = etCode.text.toString()
        if (verify(User(username, password, code))) {
            CacheUtils.save(usernameKey, username)
            CacheUtils.save(passwordKey, password)
            startActivity(Intent(this, LessonActivity::class.java))
        }
    }

    private fun verify(user: User): Boolean {
        if (user.username?.length ?: 0 < 4) {
            toast("用户名不合法")
            return false
        }
        if (user.password?.length ?: 0 < 4) {
            toast("密码不合法")
            return false
        }
        return true
    }
}