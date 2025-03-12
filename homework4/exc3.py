import numpy as np
import scipy.stats as stats
import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages

with PdfPages("plots/exc3.pdf") as pdf:
    for n in [100, 1000, 10000, 100000]:
        P = [None] * 5000
        for k in range(1,5001):
            L = 0 # time spent above 0
            S = 0 # initial position
            for X in stats.bernoulli.rvs(.5, size=n) * 2 - 1: # *2 - 1 bc rvs drops 1, 0 (and we want 1, -1)
                if S > 0 or S + X > 0:
                    L += 1
                S += X
            P[k-1] = L / n
        
        hist, edges = np.histogram(P, bins=20, density=True)
        plt.figure()
        plt.title('N = ' + str(n))
        plt.bar(edges[:-1], hist, width=np.diff(edges), align='edge', color='limegreen', label='Pn "PDF"')
        plt.xlim([0, 1])
        plt.xlabel('Pn')
        plt.ylabel('PDF')
        plt.legend()
        pdf.savefig()
        plt.close()