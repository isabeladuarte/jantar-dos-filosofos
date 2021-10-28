class Jantar {
	public static void main(String[] args) throws Exception {
		final Filosofo[] filosofos = new Filosofo[5];
		Object[] hashis = new Object[filosofos.length];

		for (int i = 0; i < hashis.length; i++) {
			hashis[i] = new Object();
		}

		for (int i = 0; i < filosofos.length; i++) {
			Object hashiEsquerdo = hashis[i];
			Object hashiDireito = hashis[(i + 1) % hashis.length];

			filosofos[i] = new Filosofo(hashiEsquerdo, hashiDireito);
			
			Thread thread = new Thread(filosofos[i], "Filosofo " + (i + 1));
			thread.start();
		}
	}
}

class Filosofo extends Thread {
	private Object hashiEsquerdo;
	private Object hashiDireito;

	public Filosofo(Object hashiEsquerdo, Object hashiDireito) {
		this.hashiEsquerdo = hashiEsquerdo;
		this.hashiDireito = hashiDireito;
	}

	// Método de realizar ação
	private void RealizarAcao(String acao) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + acao);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	@Override
	public void run() {
		try {
			while (true) {
				RealizarAcao("Pensando");
				synchronized (hashiEsquerdo) {
					RealizarAcao("Pegou hashi esquerdo");
					synchronized (hashiDireito) {
						RealizarAcao("Pegou hashi direito e comeu");
						RealizarAcao("Soltou o hashi direito");
					}
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}
}
