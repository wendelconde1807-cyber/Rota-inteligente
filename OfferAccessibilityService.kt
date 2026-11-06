package br.com.rotainteligente

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class OfferAccessibilityService : AccessibilityService() {
    private var lastNotice = 0L
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Protótipo: observa mudanças de janela/conteúdo sem coletar nem enviar dados.
        // A identificação confiável de ofertas exige adaptar regras e validar cada app.
        val now = System.currentTimeMillis()
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && now - lastNotice > 5000) {
            lastNotice = now
            // Sem pop-ups invasivos: nenhuma decisão é tomada automaticamente.
        }
    }
    override fun onInterrupt() {}
}
