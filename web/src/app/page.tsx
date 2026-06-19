"use client";

import { motion } from "framer-motion";
import { FiCpu, FiShield, FiFolder, FiCheckCircle, FiUsers, FiAward } from "react-icons/fi";
import { Button } from "@/components/ui/Button";
import { Card, CardBody, CardHeader, CardTitle } from "@/components/ui/Card";

export default function Home() {
  const setupFeatures = [
    {
      icon: <FiCpu className="text-emerald-400 w-5 h-5" />,
      title: "Clean Architecture",
      desc: "Strictly decoupled separation of layers: domain entities, core storage interfaces, contextual hooks, and modular presentation UI.",
    },
    {
      icon: <FiShield className="text-indigo-400 w-5 h-5" />,
      title: "Firebase Suite",
      desc: "Pre-configured support for Client SDK Authentication, Firestore relative databases, and Storage asset pipelines.",
    },
    {
      icon: <FiFolder className="text-violet-400 w-5 h-5" />,
      title: "Tailwind CSS v3",
      desc: "Armed with an Esports custom template featuring a dark cosmic configuration, slate gradients, and glowing accents.",
    },
  ];

  return (
    <div className="min-h-screen bg-titan-dark text-titan-muted flex flex-col justify-between py-12 px-6 lg:px-16 relative overflow-hidden">
      {/* Background Gradients */}
      <div className="absolute -top-40 -right-40 w-96 h-96 bg-titan-primary/10 rounded-full blur-3xl" />
      <div className="absolute -bottom-40 -left-40 w-96 h-96 bg-titan-accent/5 rounded-full blur-3xl" />

      {/* Header */}
      <motion.header
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        className="flex justify-between items-center z-10"
      >
        <div className="flex items-center gap-2.5">
          <div className="h-9 w-9 rounded-md bg-gradient-to-br from-titan-primary to-[#381E72] flex items-center justify-center font-bold tracking-tighter text-white shadow-[0_0_15px_rgba(208,188,255,0.4)]">
            TF
          </div>
          <span className="text-lg font-bold tracking-widest text-white uppercase">
            TITANE<span className="text-titan-primary">_FORGE</span>
          </span>
        </div>
        <div className="flex items-center gap-4">
          <span className="text-xs font-semibold tracking-widest text-titan-accent uppercase flex items-center gap-1.5 bg-titan-accent/15 px-3 py-1.5 rounded-full border border-titan-accent/20">
            <FiCheckCircle className="w-4 h-4 animate-pulse" />
            SETUP SYSTEM OK
          </span>
        </div>
      </motion.header>

      {/* Hero Display */}
      <main className="my-auto py-12 flex flex-col lg:flex-row gap-12 items-center z-10 max-w-6xl mx-auto w-full">
        <div className="flex-1 flex flex-col gap-6">
          <motion.div
            initial={{ opacity: 0, x: -30 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ delay: 0.1 }}
            className="flex flex-col gap-3"
          >
            <span className="text-xs font-bold tracking-widest text-titan-primary uppercase">
              NEXT-GEN ESPORTS MANAGEMENT ENGINE
            </span>
            <h1 className="text-4xl md:text-5xl lg:text-6xl font-black tracking-tight text-white leading-none uppercase">
              REVOLUTIONIZING <br />
              <span className="text-transparent bg-clip-text bg-gradient-to-r from-titan-primary via-violet-400 to-titan-accent">
                TEAM COMMAND
              </span>
            </h1>
          </motion.div>

          <motion.p
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.2 }}
            className="text-titan-muted text-base md:text-lg max-w-md leading-relaxed"
          >
            TITANE FORGE is fully initialized. Core architectures, Firebase SDK interfaces, and bespoke dynamic styling matrices have been compiled. Ready for operational layout deployment.
          </motion.p>

          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.3 }}
            className="flex flex-wrap gap-4 pt-2"
          >
            <Button variant="primary" size="lg">
              Launch Setup Overview
            </Button>
            <Button variant="secondary" size="lg">
              View Boilerplate Components
            </Button>
          </motion.div>
        </div>

        {/* Feature Cards Matrix */}
        <div className="flex-1 w-full grid grid-cols-1 md:grid-cols-2 gap-5">
          {setupFeatures.map((feat, idx) => (
            <Card key={idx} hoverable className="h-full flex flex-col justify-between rounded-[28px] border-titan-border bg-titan-surface">
              <CardHeader className="flex justify-between items-center border-none pb-0">
                <div className="p-2.5 rounded-md bg-titan-surface border border-titan-border">
                  {feat.icon}
                </div>
              </CardHeader>
              <div className="pt-3">
                <CardTitle className="text-base uppercase tracking-wide text-white font-bold mb-1.5">
                  {feat.title}
                </CardTitle>
                <CardBody className="text-titan-muted text-xs leading-normal">
                  {feat.desc}
                </CardBody>
              </div>
            </Card>
          ))}
          
          {/* Quick Metrics Live Showcase */}
          <Card className="h-full bg-gradient-to-br from-titan-primary/5 to-titan-dark border-titan-border rounded-[28px] flex flex-col justify-center p-5">
            <div className="flex items-center gap-3">
              <div className="h-8 w-8 rounded-full bg-titan-primary/20 flex items-center justify-center text-titan-primary font-bold text-sm">
                <FiAward />
              </div>
              <div>
                <div className="text-white font-bold text-xs uppercase tracking-wide">Production Ready</div>
                <div className="text-titan-muted text-[11px]">System structure complies with modern Web standards.</div>
              </div>
            </div>
          </Card>
        </div>
      </main>

      {/* Footer / Info */}
      <motion.footer
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ delay: 0.4 }}
        className="text-center text-xs text-zinc-600 tracking-wider flex justify-between items-center border-t border-zinc-800/60 pt-6 z-10"
      >
        <span>TITANE FORGE ARCHITECTURE SUITE • VERSION 1.0.0</span>
        <span className="hover:text-indigo-400 transition-colors cursor-pointer">DOCUMENTATION</span>
      </motion.footer>
    </div>
  );
}
